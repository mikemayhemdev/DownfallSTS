package expansioncontent.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

abstract public class AbstractDownfallCard extends CustomCard {
    //This abstract should contain common methods and variables, it will also help with transition to use Atlases for card art later.

    //Variables
        //Second Magic - Downfall Magic (DM)
            public int downfallMagic;
            public int baseDownfallMagic;
            public boolean upgradedDownfall;
            public boolean isDownfallModified;
        //Third Magic - Second Downfall Magic (DM2)
            public int secondDownfall;
            public int baseSecondDownfall;
            public boolean upgradedSecondDownfall;
            public boolean isSecondDownfallModified;
        //Tag Magic (TM)
            public int tagMagic;
            public int baseTagMagic;
            public boolean upgradedTagMagic;
            public boolean isTagMagicModified;
            /* What does TM (Tag Magic) mean and how it is supposed to be used you ask?
                It is simple the intended use for it is to combine it with card tags, but that might be too vague so an example:
                    A card that applies Goop would have TM_GOOP tag, and Powers that increase that can just check if card has correct tag (also if it is an instance of AbstractDownfallCard later to prevent crashes).
                    List of effects that TM should be eventually used on Goop, Self Damage, Brace, Soulburn, Counter, Vigor, and possibly more in the future.
                    But what if the card gives you both Vigor and Counter, then you need to calculate bonus for each tag (similar to how Snecko does random amount of damage).
             */
    //Card stuff
        protected final CardStrings cardStrings;
        protected final String NAME;
        protected String DESCRIPTION;
        protected String UPGRADE_DESCRIPTION;
        protected String[] EXTENDED_DESCRIPTION;

    public AbstractDownfallCard(final String id, String img, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, "ERROR", imgCheck(img, type), cost, "ERROR", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    private static String imgCheck(String imgPath, CardType type) {
        if (Gdx.files.internal(imgPath).exists())
            return imgPath;
        switch (type) {
            case ATTACK:
                return "expansioncontentResources/images/cards/Placeholder/Attack.png";
            case POWER:
                return "expansioncontentResources/images/cards/Placeholder/Power.png";
            default:
                return "expansioncontentResources/images/cards/Placeholder/Skill.png";
        }
    }

    //Variables and upgrades

    public void resetAttributes() {
        super.resetAttributes();
        downfallMagic = baseDownfallMagic;
        isDownfallModified = false;
        secondDownfall = baseSecondDownfall;
        isSecondDownfallModified = false;
        tagMagic = baseTagMagic;
        isTagMagicModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedDownfall) {
            downfallMagic = baseDownfallMagic;
            isDownfallModified = true;
        }
        if (upgradedSecondDownfall) {
            secondDownfall = baseSecondDownfall;
            isSecondDownfallModified = true;
        }
        if (upgradedTagMagic) {
            tagMagic = baseTagMagic;
            isTagMagicModified = true;
        }
    }

    public void upgradeDownfall(int amount) {
        baseDownfallMagic += amount;
        downfallMagic = baseDownfallMagic;
        upgradedDownfall = true;
    }

    public void upgradeSecondDownfall(int amount) {
        baseSecondDownfall += amount;
        secondDownfall = baseSecondDownfall;
        upgradedSecondDownfall = true;
    }

    public void upgradeTagMagic(int amount) {
        baseTagMagic += amount;
        tagMagic = baseTagMagic;
        upgradedTagMagic = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    abstract public void upp();

    //Simple Methods

    protected void atb(AbstractGameAction action) {
        addToBot(action);
    }

    protected void att(AbstractGameAction action) {
        addToTop(action);
    }

    protected DamageInfo makeInfo() {
        return makeInfo(damageTypeForTurn);
    }

    private DamageInfo makeInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, type);
    }

    public void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, makeInfo(), fx));
    }

    public void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public void makeInHandTop(AbstractCard c, int i) {
        att(new MakeTempCardInHandAction(c, i));
    }

    public void makeInHandTop(AbstractCard c) {
        makeInHandTop(c, 1);
    }

    public void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true)); // This is suspect
    }

    public void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public ArrayList<AbstractMonster> monsterList() {
        ArrayList<AbstractMonster> q = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDying && !m.isDead) q.add(m);
        }
        return q;
    }

    public void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public WeakPower autoWeak(AbstractMonster m, int i) {
        return new WeakPower(m, i, false);
    }

    public VulnerablePower autoVuln(AbstractMonster m, int i) {
        return new VulnerablePower(m, i, false);
    }
}

package expansioncontent.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
            public boolean isDownfallUpgraded;
            public boolean isDownfallModified;
        //Third Magic - Second Downfall Magic (DM2)
            public int secondDownfall;
            public int baseSecondDownfall;
            public boolean isSecondDownfallUpgraded;
            public boolean isSecondDownfallModified;

    public AbstractDownfallCard(final String id, final String name, String img, final int cost, final String description, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, name, imgCheck(img, type), cost, description, type, color, rarity, target);
    }

    private static String imgCheck(String imgPath, CardType type) {
        if (imgPath != null && Gdx.files.internal(imgPath).exists())
            return imgPath;
        switch (type) {
            case ATTACK:
                return "expansioncontentResources/images/cards/placeholder/Attack.png";
            case POWER:
                return "expansioncontentResources/images/cards/placeholder/Power.png";
            default:
                return "expansioncontentResources/images/cards/placeholder/Skill.png";
        }
    }

    //Variables and upgrades

    public void resetAttributes() {
        super.resetAttributes();
        downfallMagic = baseDownfallMagic;
        isDownfallModified = false;
        secondDownfall = baseSecondDownfall;
        isSecondDownfallModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (isDownfallUpgraded) {
            downfallMagic = baseDownfallMagic;
            isDownfallModified = true;
        }
        if (isSecondDownfallUpgraded) {
            secondDownfall = baseSecondDownfall;
            isSecondDownfallModified = true;
        }
    }

    public void upgradeDownfall(int amount) {
        baseDownfallMagic += amount;
        downfallMagic = baseDownfallMagic;
        isDownfallUpgraded = true;
    }

    public void upgradeSecondDownfall(int amount) {
        baseSecondDownfall += amount;
        secondDownfall = baseSecondDownfall;
        isSecondDownfallUpgraded = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public void upp() {

    }

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

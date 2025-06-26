package hermit.cards;

import champ.relics.DuelingGlove;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.relics.TagTeamwork;
import hermit.HermitMod;
import hermit.actions.HandSelectAction;
import hermit.characters.hermit;
import hermit.relics.Spyglass;
import hermit.util.Wiz;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Malice extends AbstractHermitCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Malice.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("malice.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    private static final int DAMAGE = 16;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public Malice() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        loadJokeCardImage(this, "malice.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HandSelectAction(1, (c) -> true, list -> {}, list -> {
            boolean isCurse = false;

            for (AbstractCard c : list)
            {
                Wiz.p().hand.moveToExhaustPile(c);
                if (c.color == CardColor.CURSE)
                    isCurse = true;
            }

            if (isCurse) {
                Wiz.att(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.baseDamage), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            } else {
                Wiz.att(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                //schrodinger's card target
                if (AbstractDungeon.player.hasRelic(DuelingGlove.ID)) {
                    AbstractDungeon.player.getRelic(DuelingGlove.ID).onTrigger(m);
                }
            }

        }, uiStrings.TEXT[0],false,false,false));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}

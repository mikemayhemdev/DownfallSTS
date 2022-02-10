package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

import static hermit.HermitMod.*;

public class Roulette extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Roulette.class.getSimpleName());
    public static final String IMG = makeCardPath("roulette.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2;

    private static final int DAMAGE = 18;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public Roulette() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        loadJokeCardImage(this, "roulette.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int cards = p.hand.size();
        if (AbstractDungeon.player.hand.group.indexOf(this)>=0)
            cards-=1;

        this.addToBot(new AttackDamageRandomEnemyAction(this, EnumPatch.HERMIT_GUN2));
        CardCrawlGame.sound.playAV(makeID("SPIN"), 1.0f, 1.25f); // Sound Effect
        this.addToBot(new DiscardAction(p, p, cards, false));
        this.addToBot(new DrawCardAction(p, cards));

    }



    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
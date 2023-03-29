package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.util.Wiz;

import static hermit.HermitMod.*;

public class HighCaliber extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(HighCaliber.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardStrings strikeStrings = CardCrawlGame.languagePack.getCardStrings(Strike_Hermit.ID);

    public static final String IMG = makeCardPath("high_caliber.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/

    public HighCaliber() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        cardsToPreview = new Strike_Hermit();

        cardsToPreview.baseDamage += 6;
        cardsToPreview.upgradedDamage = true;
        cardsToPreview.upgraded = true;

        cardsToPreview.name = strikeStrings.NAME + "+" + 2;

        cardsToPreview.initializeDescription();

        exhaust = true;
        loadJokeCardImage(this, "heavy_caliber.png");
    }

    @Override
    public void applyPowers() {
        int num = 2;
        cardsToPreview.baseDamage = 6;

        if (upgraded)
            num++;

        for(int a=0;a<num;a++)
        {
            cardsToPreview.baseDamage += 3;
            cardsToPreview.upgradedDamage = true;
            cardsToPreview.upgraded = true;

            cardsToPreview.name = strikeStrings.NAME + "+" + num;
        }

        cardsToPreview.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), EnumPatch.HERMIT_GUN));
        Wiz.atb(new AbstractGameAction(){
            @Override
            public void update()
            {
                AbstractCard s = (new Strike_Hermit()).makeCopy();

                int num = 2;
                s.baseDamage = 6;

                if (upgraded)
                    num++;

                for(int a=0;a<num;a++)
                {
                    s.baseDamage += 3;
                    s.upgradedDamage = true;
                    s.upgraded = true;

                    s.name = strikeStrings.NAME + "+" + num;
                }
                Wiz.att(new MakeTempCardInHandAction(s, 1));

                isDone = true;
            }
        });
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            rawDescription = UPGRADE_DESCRIPTION;
            cardsToPreview.baseDamage += 3;
            cardsToPreview.name = strikeStrings.NAME + "+" + 3;
            cardsToPreview.initializeDescription();
            initializeDescription();
        }
    }
}

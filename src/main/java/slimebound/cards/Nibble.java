package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PreventSlimeDecayPower;
import sneckomod.TheSnecko;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;


public class Nibble extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Nibble";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/acceleratetoxins.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 0;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Nibble() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        baseDamage = damage = 4;
        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> (c.color != AbstractDungeon.player.getCardColor() || c.cost > 0) || c.hasTag(AbstractCard.CardTags.HEALING));
        AbstractCard c = possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
        if (upgraded) c.upgrade();
        this.addToBot(new MakeTempCardInHandAction(c, true));
    }

    public AbstractCard makeCopy() {
        return new Nibble();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}


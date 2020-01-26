package slimebound.cards;


import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.patches.AbstractCardEnum;


public class Strike_Slimebound extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Strike_Slimebound";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/attackSlime.png";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final CardStrings cardStrings;

    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;

    public Strike_Slimebound() {
        super(ID, NAME, slimebound.SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.baseDamage = 6;
        this.tags.add(BaseModCardTags.BASIC_STRIKE);
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }


    public AbstractCard makeCopy() {
        return new Strike_Slimebound();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}



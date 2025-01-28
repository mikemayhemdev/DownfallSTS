package downfall.cards.curses;


import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import collector.cards.OnOtherCardExhaustInHand;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.cardmods.PropertiesMod;


public class Haunted extends CustomCard implements OnOtherCardExhaustInHand {
    public static final String ID = downfallMod.makeID("Haunted");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/haunted.png");
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = -2;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Haunted() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 1;

        this.isEthereal = true;
        tags.add(downfallMod.DOWNFALL_CURSE);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerWhenDrawn() {
//        super.triggerWhenDrawn();
//        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
//            @Override
//            public void update() {
//                isDone = true;
//                for (AbstractCard c : AbstractDungeon.player.hand.group) {
//                    if (!c.isEthereal) {
//                        CardModifierManager.addModifier(c, new PropertiesMod(PropertiesMod.supportedProperties.ETHEREAL, false));
//                        c.superFlash(Color.PURPLE.cpy());
//                    }
//                }
//            }
//        });
    }

    @Override
    public void atTurnStart() {
    }

    @Override
    public void onOtherCardExhaustWhileInHand(AbstractCard card) {
        flash(Color.PURPLE.cpy());
        this.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }


    public AbstractCard makeCopy() {
        return new Haunted();
    }

    public void upgrade() {
    }
}



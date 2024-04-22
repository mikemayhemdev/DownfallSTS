package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.DrawUntilNonEtherealAction;
import theHexaghost.util.HexaPurpleTextInterface;

public class Haunt extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("Haunt");

    //haunted hand

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    public static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public Haunt() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
        HexaMod.loadJokeCardImage(this, "HauntedHand.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(p, magicNumber));
        atb(new DrawCardAction(1));
        atb(new DrawUntilNonEtherealAction());
//        atb(new AbstractGameAction() {
//            @Override
//            public void update() {
//                isDone = true;
//                for (AbstractCard c : p.hand.group) {
//                    if (!c.isEthereal) {
//                        CardModifierManager.addModifier(c, new PropertiesMod(PropertiesMod.supportedProperties.ETHEREAL, false));
//                        c.superFlash(Color.PURPLE.cpy());
//                    }
//                }
//            }
//        });
    }

    @Override
    public void afterlife() {
        atb(new DrawCardAction(1));
        atb(new DrawUntilNonEtherealAction());

//        atb(new DrawCardAction(AbstractDungeon.player, magicNumber));
//        atb(new AbstractGameAction() {
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

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
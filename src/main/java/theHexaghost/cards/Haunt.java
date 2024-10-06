package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.DrawUntilNonEtherealAction;
import theHexaghost.util.HexaPurpleTextInterface;

public class Haunt extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("HauntedHand");

    //haunted hand

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    public static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public Haunt() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
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



    @Override //zhs card text thing
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if(Settings.language == Settings.GameLanguage.ZHS && this.description!=null && this.description.size()>=1 ) {
            for(int i=0;i<this.description.size();i++){
                if(this.description.get(i).text.equals("[#e087a4]。[]") || this.description.get(i).text.equals("[#e087a4]。[] ") ){
                    StringBuilder sb = new StringBuilder();
                    this.description.get(i-1).text = sb.append(this.description.get(i-1).text).append("[#e087a4]。[]").toString();
                    this.description.remove(i);
                }
            }
        }
    }


    // to still show afterlife tooltip. because the format [purple]hexamod:afterlife[] doesnt get displayed correctly
    // we are only using [purple]afterlife[] here for easier text comprehension for new players, but doing this
    // means we dont have the keyword tooltip so we need to manually add it
    // but after I tried adding it in the constrcutor it turns out sometimes who knows why it wont be added
    // and this way seems to work
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(afterlife_name);
    }
}
//Toxic Personality - Whenever you apply a debuff to an enemy, apply !M! Venom.

//This was using an old version of Venom but honestly it was just too simple and too strong at the same time.

//Queen of Pentacles was just cooler.

//package sneckomod.cards;
//
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import downfall.util.CardIgnore;
//import sneckomod.SneckoMod;
//import sneckomod.powers.ToxicPersonalityPower;
//
//import java.util.ArrayList;

//@Deprecated
//@CardIgnore
//public class ToxicPersonality extends AbstractSneckoCard {
//
//    public final static String ID = makeID("ToxicPersonality");
//
//    //stupid intellij stuff POWER, SELF, RARE
//
//    private static final int MAGIC = 5;
//    private static final int UPG_MAGIC = 2;
//
//    public ToxicPersonality() {
//        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
//        baseMagicNumber = magicNumber = MAGIC;
//        SneckoMod.loadJokeCardImage(this, "AceOfWands.png");
//    }
//
//    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
//        for (AbstractCard alreadyHave : cardsList) {
//            if (alreadyHave.cardID.equals(card.cardID)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        applyToSelf(new ToxicPersonalityPower(magicNumber));
//    }
//
//    @Override
//    public void onObtainCard() {
//        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
//        while (cardsToReward.size() < 3) {
//            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(
//                    c -> (c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE) &&
//                            ((((c.rawDescription.contains("Apply") || c.rawDescription.contains("apply") || c.rawDescription.contains("applies")
//                                    || c.rawDescription.contains("Lick") || c.rawDescription.contains("Debuff") || c.rawDescription.contains("Steal")
//                                    || c.name.contains("Disarm") || c.name.contains("Choke") || c.name.contains("Talk to the Hand") || c.name.contains("Cursed Wail")
//                                    || c.name.contains("Undervolt")) || c.name.contains("Dark Lord Form")))));
//
//
//
//            if (!cardListDuplicate(cardsToReward, newCard)) {
//                cardsToReward.add(newCard.makeCopy());
//            }
//        }
//
//        SneckoMod.addGift(cardsToReward);
//        ;
//    }
//
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            upgradeMagicNumber(UPG_MAGIC);
//        }
//    }}